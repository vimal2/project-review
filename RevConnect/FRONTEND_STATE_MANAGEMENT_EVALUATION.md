# Frontend State Management Evaluation

**Project:** RevConnect
**Framework:** Angular 16.2.0 with TypeScript
**Date:** March 2026

---

## Executive Summary

This document evaluates the RevConnect frontend state management architecture, specifically analyzing the use of **shared app state with derived state per functional unit**. The evaluation reveals a lightweight service-based approach with significant gaps in state management patterns that will impact scalability for a social network application.

**Overall Score: 2.5/5**

---

## Table of Contents

1. [Current Architecture Overview](#1-current-architecture-overview)
2. [Shared App State Assessment](#2-shared-app-state-assessment)
3. [Derived State Analysis by Functional Unit](#3-derived-state-analysis-by-functional-unit)
4. [Identified Issues](#4-identified-issues)
5. [Recommended Solutions](#5-recommended-solutions)
6. [Implementation Guide](#6-implementation-guide)
7. [Migration Strategy](#7-migration-strategy)

---

## 1. Current Architecture Overview

### Directory Structure

```
src/app/
├── core/
│   ├── guards/
│   │   └── auth.guard.ts
│   ├── interceptors/
│   │   └── token.interceptor.ts
│   └── services/
│       ├── auth.service.ts          # Has BehaviorSubject state
│       ├── notification.service.ts   # Has BehaviorSubject state
│       ├── post.service.ts           # API only, no state
│       ├── user.service.ts           # API only, no state
│       └── network.service.ts        # API only, no state
├── features/
│   ├── feed-page/
│   ├── network/
│   │   └── connections/
│   ├── notifications/
│   │   └── notification-list/
│   └── users/
│       ├── profile-edit/
│       └── profile-view/
├── shared/
│   ├── models/
│   │   └── models.ts
│   └── services/                     # Empty - not utilized
└── app.module.ts
```

### Current State Management Approach

| Layer | Implementation | Pattern |
|-------|---------------|---------|
| Authentication | `AuthService` with BehaviorSubject | Reactive (Good) |
| Notifications | `NotificationService` with BehaviorSubject | Reactive (Good) |
| Posts/Feed | Component-local arrays | Not shared |
| Connections | Component-local arrays | Not shared |
| User Profile | Component-local + FormGroup | Not shared |

---

## 2. Shared App State Assessment

### Current Implementation: **PARTIAL**

#### 2.1 Authentication State (`auth.service.ts`) - **GOOD**

```typescript
// Current Implementation - PROPERLY REACTIVE
@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUserSubject = new BehaviorSubject<AuthResponse | null>(this.loadUser());
  currentUser$ = this.currentUserSubject.asObservable();

  getCurrentUser(): AuthResponse | null {
    return this.currentUserSubject.value;
  }

  getCurrentUserId(): number | null {
    return this.currentUserSubject.value?.id ?? null;
  }

  getCurrentUsername(): string | null {
    return this.currentUserSubject.value?.username ?? null;
  }

  getUserRole(): string | null {
    return this.currentUserSubject.value?.role ?? null;
  }

  login(request: LoginRequest): Observable<ApiResponse<AuthResponse>> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.API}/login`, request).pipe(
      tap(res => this.storeSession(res.data))
    );
  }

  private storeSession(user: AuthResponse): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }
}
```

**Assessment:** This is a well-implemented shared state service with reactive patterns.

#### 2.2 Notification State (`notification.service.ts`) - **GOOD**

```typescript
// Current Implementation - PROPERLY REACTIVE
@Injectable({ providedIn: 'root' })
export class NotificationService {
  private unreadCount = new BehaviorSubject<number>(0);
  unreadCount$ = this.unreadCount.asObservable();

  startPolling(): void {
    interval(30000).pipe(
      switchMap(() => this.getUnreadCount())
    ).subscribe();
  }

  private getUnreadCount(): Observable<number> {
    return this.http.get<ApiResponse<number>>(`${this.API}/unread-count`).pipe(
      tap(res => this.unreadCount.next(res.data))
    );
  }
}
```

**Assessment:** Good reactive pattern with polling mechanism.

#### 2.3 Feed/Posts State (Component-Local) - **POOR**

```typescript
// feed-page.component.ts - NO SHARED STATE
export class FeedPageComponent implements OnInit {
  posts: Post[] = [];
  loading = true;
  page = 0;
  totalPages = 0;
  newPostContent = '';
  newPostHashtags = '';
  creatingPost = false;
  searchQuery: string = '';
  users: any[] = [];

  // Direct HTTP call in component (should use service)
  loadFeed(): void {
    this.http.get<ApiResponse<PageResponse<Post>>>(
      `${environment.apiUrl}/feed?page=${this.page}&size=10`
    ).subscribe({
      next: (res) => {
        this.posts = res.data.content;  // Direct assignment
        this.totalPages = res.data.totalPages;
        this.loading = false;
      }
    });
  }

  // Direct array mutation
  createPost(): void {
    this.postService.createPost({...}).subscribe({
      next: (res) => {
        this.posts.unshift(res.data);  // Mutation!
      }
    });
  }
}
```

**Issues:**
- State isolated to component
- Direct HTTP calls bypassing service layer
- Direct array mutations
- No sharing with other components that might need posts

#### 2.4 Connections State (Component-Local) - **POOR**

```typescript
// connections.component.ts - NO SHARED STATE
export class ConnectionsComponent implements OnInit {
  connections: Connection[] = [];
  pendingRequests: Connection[] = [];
  sentRequests: Connection[] = [];
  allUsers: User[] = [];
  sentUserIds = new Set<number>();
  currentUserId = 0;

  ngOnInit(): void {
    // Direct localStorage access (should use AuthService)
    const stored = localStorage.getItem('currentUser');
    if (stored) {
      this.currentUserId = JSON.parse(stored).id;
    }
    this.loadConnections();
  }

  loadConnections(): void {
    this.networkService.getConnections().subscribe(r => {
      this.connections = r.data;  // Direct assignment
    });
    this.networkService.getPendingRequests().subscribe(r => {
      this.pendingRequests = r.data;  // Direct assignment
    });
  }
}
```

**Issues:**
- Bypasses AuthService for user ID
- Multiple separate subscriptions
- No combined/derived state

### State Sharing Matrix

| Data | FeedPage | Connections | Profile | Notifications | Shared? |
|------|----------|-------------|---------|---------------|---------|
| Current User | Via Service | localStorage | Via Service | Via Service | **PARTIAL** |
| Posts | Yes | No | Yes | No | **NO** |
| Connections | No | Yes | No | No | **NO** |
| Notifications | No | No | No | Yes | **NO** |
| Unread Count | Via Navbar | No | No | Via Service | **YES** |

---

## 3. Derived State Analysis by Functional Unit

### 3.1 Feed Feature

**Current:** No derived state

```typescript
// Missing derived state examples:
// - Posts filtered by hashtag
// - Posts by current user
// - Like counts aggregated
// - Trending hashtags computed
```

**What exists:**
- Raw posts array
- Manual filtering in template with `*ngIf`

### 3.2 Connections Feature

**Current:** Manual Set for tracking (basic caching)

```typescript
// connections.component.ts
sentUserIds = new Set<number>();

// Manual population
this.sentRequests.forEach(req =>
  this.sentUserIds.add(req.addressee.id)
);

// Usage for button state
canSendRequest(userId: number): boolean {
  return !this.sentUserIds.has(userId);
}
```

**Assessment:** Basic derived state via Set, but not reactive.

### 3.3 Notifications Feature

**Current:** No derived state

```typescript
// notification-list.component.ts
notifications: Notification[] = [];
loading = true;

// No grouping, filtering, or computed properties
```

**Missing:**
- Notifications grouped by type
- Notifications grouped by date
- Read vs unread filtering

### 3.4 Profile Feature

**Current:** Form-based state only

```typescript
// profile-edit.component.ts
profileForm!: FormGroup;
loading = false;
saved = false;
error = '';
isBusinessOrCreator = false;

// Derived state from role
ngOnInit(): void {
  const role = this.authService.getUserRole();
  this.isBusinessOrCreator = role === 'BUSINESS' || role === 'CREATOR';
}
```

**Assessment:** Simple derived boolean, not reactive to role changes.

### 3.5 Profile View Feature

**Current:** Hardcoded mock data (incomplete implementation)

```typescript
// profile-view.component.ts
profile: any = {
  id: 1,
  name: 'Gopala',
  bio: 'Java Developer',
  role: 'CREATOR'
};

// Commented out actual service call
// ngOnInit(): void {
//   this.userService.getUserById(this.userId).subscribe(...)
// }

// Fake loading
setTimeout(() => {
  this.postsLoading = false;
}, 500);
```

**Assessment:** Non-functional, needs implementation.

### Derived State Summary

| Functional Unit | Derived State | Memoization | Quality |
|-----------------|---------------|-------------|---------|
| Feed | None | N/A | Poor |
| Connections | Set-based tracking | Manual | Fair |
| Notifications | None | N/A | Poor |
| Profile Edit | Simple boolean | None | Fair |
| Profile View | None (mock data) | N/A | Poor |
| Auth | Getter methods | None | Fair |

---

## 4. Identified Issues

### 4.1 Critical Issues

#### Issue 1: Inconsistent State Access Patterns

```typescript
// GOOD: Using AuthService
const role = this.authService.getUserRole();

// BAD: Direct localStorage access (same component)
const stored = localStorage.getItem('currentUser');
this.currentUserId = JSON.parse(stored).id;
```

#### Issue 2: No Unsubscription Patterns

```typescript
// feed-page.component.ts - Memory leak risk
ngOnInit(): void {
  this.loadFeed();
  this.authService.currentUser$.subscribe(user => {
    // Never unsubscribed!
  });
}
// No ngOnDestroy implementation
```

#### Issue 3: Direct Array Mutations

```typescript
// Creates new reference issues, breaks change detection optimization
this.posts.unshift(res.data);
this.posts.splice(idx, 1);
```

#### Issue 4: HTTP Calls in Components

```typescript
// feed-page.component.ts - Should use PostService
this.http.get<ApiResponse<PageResponse<Post>>>(
  `${environment.apiUrl}/feed?page=${this.page}&size=10`
).subscribe({...});
```

#### Issue 5: Race Conditions

```typescript
// connections.component.ts - Multiple uncoordinated requests
ngOnInit(): void {
  this.loadConnections();
  this.loadPending();
  this.loadSent();
  this.loadAllUsers();
  // No coordination, no loading state management
}
```

### 4.2 Moderate Issues

| Issue | Impact | Location |
|-------|--------|----------|
| No optimistic updates | Poor UX during mutations | Feed, Connections |
| No error state management | Silent failures | All components |
| Hardcoded mock data | Non-functional feature | Profile View |
| No loading coordination | Inconsistent UI | Connections |
| Form state mixed with data | Coupling | Profile Edit |

### 4.3 Data Flow Diagram

```
Current Flow (Problematic):

┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│    Feed     │    │ Connections │    │  Profile    │
│  Component  │    │  Component  │    │  Component  │
│  [posts]    │    │[connections]│    │  [profile]  │
└──────┬──────┘    └──────┬──────┘    └──────┬──────┘
       │                  │                  │
       │ HTTP direct      │                  │
       ▼                  ▼                  ▼
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  (bypass)   │    │NetworkService│   │ UserService │
└──────┬──────┘    └──────┬──────┘    └──────┬──────┘
       │                  │                  │
       └──────────────────┼──────────────────┘
                          │
                          ▼
                    ┌───────────┐
                    │  Backend  │
                    └───────────┘

❌ Feed bypasses service layer
❌ No shared state between features
❌ Duplicate user data fetching
❌ No cache invalidation
```

---

## 5. Recommended Solutions

### 5.1 Implement Centralized Feed State Service

```typescript
// src/app/core/state/feed-state.service.ts
import { Injectable, computed, signal } from '@angular/core';
import { BehaviorSubject, Observable, tap, map, shareReplay } from 'rxjs';
import { Post, PageResponse } from '../../shared/models/models';
import { PostService } from '../services/post.service';

interface FeedState {
  posts: Post[];
  loading: boolean;
  error: string | null;
  page: number;
  totalPages: number;
  hasMore: boolean;
}

@Injectable({ providedIn: 'root' })
export class FeedStateService {
  // Base state using signals (Angular 16+)
  private readonly state = signal<FeedState>({
    posts: [],
    loading: false,
    error: null,
    page: 0,
    totalPages: 0,
    hasMore: true
  });

  // Selectors (direct state access)
  readonly posts = computed(() => this.state().posts);
  readonly loading = computed(() => this.state().loading);
  readonly error = computed(() => this.state().error);
  readonly hasMore = computed(() => this.state().hasMore);

  // Derived state (computed from base state)
  readonly postCount = computed(() => this.state().posts.length);

  readonly postsByUser = computed(() => {
    const posts = this.state().posts;
    return (userId: number) => posts.filter(p => p.author.id === userId);
  });

  readonly postsWithHashtag = computed(() => {
    const posts = this.state().posts;
    return (hashtag: string) => posts.filter(p =>
      p.hashtags?.some(h => h.toLowerCase() === hashtag.toLowerCase())
    );
  });

  readonly trendingHashtags = computed(() => {
    const posts = this.state().posts;
    const hashtagCounts = new Map<string, number>();

    posts.forEach(post => {
      post.hashtags?.forEach(tag => {
        const lower = tag.toLowerCase();
        hashtagCounts.set(lower, (hashtagCounts.get(lower) || 0) + 1);
      });
    });

    return Array.from(hashtagCounts.entries())
      .sort((a, b) => b[1] - a[1])
      .slice(0, 10)
      .map(([tag, count]) => ({ tag, count }));
  });

  readonly likeStats = computed(() => {
    const posts = this.state().posts;
    return {
      totalLikes: posts.reduce((sum, p) => sum + (p.likeCount || 0), 0),
      averageLikes: posts.length > 0
        ? posts.reduce((sum, p) => sum + (p.likeCount || 0), 0) / posts.length
        : 0,
      mostLiked: [...posts].sort((a, b) => (b.likeCount || 0) - (a.likeCount || 0))[0]
    };
  });

  constructor(private postService: PostService) {}

  // Actions
  loadFeed(page: number = 0): void {
    this.state.update(s => ({ ...s, loading: true, error: null }));

    this.postService.getFeed(page).subscribe({
      next: (res) => {
        const newPosts = page === 0
          ? res.data.content
          : [...this.state().posts, ...res.data.content];

        this.state.update(s => ({
          ...s,
          posts: newPosts,
          page: res.data.number,
          totalPages: res.data.totalPages,
          hasMore: !res.data.last,
          loading: false
        }));
      },
      error: (err) => {
        this.state.update(s => ({
          ...s,
          error: err.message || 'Failed to load feed',
          loading: false
        }));
      }
    });
  }

  loadMore(): void {
    if (this.state().hasMore && !this.state().loading) {
      this.loadFeed(this.state().page + 1);
    }
  }

  addPost(post: Post): void {
    this.state.update(s => ({
      ...s,
      posts: [post, ...s.posts]
    }));
  }

  updatePost(postId: number, updates: Partial<Post>): void {
    this.state.update(s => ({
      ...s,
      posts: s.posts.map(p =>
        p.id === postId ? { ...p, ...updates } : p
      )
    }));
  }

  removePost(postId: number): void {
    this.state.update(s => ({
      ...s,
      posts: s.posts.filter(p => p.id !== postId)
    }));
  }

  likePost(postId: number): void {
    // Optimistic update
    this.state.update(s => ({
      ...s,
      posts: s.posts.map(p =>
        p.id === postId
          ? { ...p, likeCount: (p.likeCount || 0) + 1, liked: true }
          : p
      )
    }));

    this.postService.likePost(postId).subscribe({
      error: () => {
        // Rollback on error
        this.state.update(s => ({
          ...s,
          posts: s.posts.map(p =>
            p.id === postId
              ? { ...p, likeCount: (p.likeCount || 0) - 1, liked: false }
              : p
          )
        }));
      }
    });
  }

  refresh(): void {
    this.state.update(s => ({ ...s, posts: [], page: 0 }));
    this.loadFeed(0);
  }
}
```

### 5.2 Implement Connections State Service

```typescript
// src/app/core/state/connections-state.service.ts
import { Injectable, computed, signal, inject } from '@angular/core';
import { Connection, User } from '../../shared/models/models';
import { NetworkService } from '../services/network.service';
import { AuthStateService } from './auth-state.service';
import { forkJoin } from 'rxjs';

interface ConnectionsState {
  connections: Connection[];
  pendingRequests: Connection[];
  sentRequests: Connection[];
  suggestedUsers: User[];
  loading: boolean;
  error: string | null;
}

@Injectable({ providedIn: 'root' })
export class ConnectionsStateService {
  private readonly networkService = inject(NetworkService);
  private readonly authState = inject(AuthStateService);

  private readonly state = signal<ConnectionsState>({
    connections: [],
    pendingRequests: [],
    sentRequests: [],
    suggestedUsers: [],
    loading: false,
    error: null
  });

  // Selectors
  readonly connections = computed(() => this.state().connections);
  readonly pendingRequests = computed(() => this.state().pendingRequests);
  readonly sentRequests = computed(() => this.state().sentRequests);
  readonly suggestedUsers = computed(() => this.state().suggestedUsers);
  readonly loading = computed(() => this.state().loading);

  // Derived state
  readonly connectionCount = computed(() => this.state().connections.length);
  readonly pendingCount = computed(() => this.state().pendingRequests.length);
  readonly sentCount = computed(() => this.state().sentRequests.length);

  readonly sentUserIds = computed(() =>
    new Set(this.state().sentRequests.map(r => r.addressee.id))
  );

  readonly connectedUserIds = computed(() =>
    new Set(this.state().connections.map(c =>
      c.requester.id === this.authState.userId()
        ? c.addressee.id
        : c.requester.id
    ))
  );

  readonly canSendRequest = computed(() => {
    const sent = this.sentUserIds();
    const connected = this.connectedUserIds();
    const currentUserId = this.authState.userId();

    return (userId: number) =>
      userId !== currentUserId &&
      !sent.has(userId) &&
      !connected.has(userId);
  });

  readonly connectionsByStatus = computed(() => ({
    accepted: this.state().connections.filter(c => c.status === 'ACCEPTED'),
    pending: this.state().pendingRequests,
    sent: this.state().sentRequests
  }));

  // Actions
  loadAll(): void {
    this.state.update(s => ({ ...s, loading: true, error: null }));

    forkJoin({
      connections: this.networkService.getConnections(),
      pending: this.networkService.getPendingRequests(),
      sent: this.networkService.getSentRequests(),
      suggested: this.networkService.getSuggestedUsers()
    }).subscribe({
      next: (results) => {
        this.state.update(s => ({
          ...s,
          connections: results.connections.data,
          pendingRequests: results.pending.data,
          sentRequests: results.sent.data,
          suggestedUsers: results.suggested.data,
          loading: false
        }));
      },
      error: (err) => {
        this.state.update(s => ({
          ...s,
          error: err.message || 'Failed to load connections',
          loading: false
        }));
      }
    });
  }

  sendRequest(userId: number): void {
    // Optimistic update
    const user = this.state().suggestedUsers.find(u => u.id === userId);
    if (user) {
      const newRequest: Connection = {
        id: Date.now(), // Temporary ID
        requester: { id: this.authState.userId()! } as User,
        addressee: user,
        status: 'PENDING',
        createdAt: new Date().toISOString()
      };

      this.state.update(s => ({
        ...s,
        sentRequests: [...s.sentRequests, newRequest],
        suggestedUsers: s.suggestedUsers.filter(u => u.id !== userId)
      }));
    }

    this.networkService.sendConnectionRequest(userId).subscribe({
      next: (res) => {
        // Update with real data from server
        this.state.update(s => ({
          ...s,
          sentRequests: s.sentRequests.map(r =>
            r.addressee.id === userId ? res.data : r
          )
        }));
      },
      error: () => {
        // Rollback
        this.loadAll();
      }
    });
  }

  acceptRequest(connectionId: number): void {
    const request = this.state().pendingRequests.find(r => r.id === connectionId);

    // Optimistic update
    this.state.update(s => ({
      ...s,
      pendingRequests: s.pendingRequests.filter(r => r.id !== connectionId),
      connections: request ? [...s.connections, { ...request, status: 'ACCEPTED' }] : s.connections
    }));

    this.networkService.acceptRequest(connectionId).subscribe({
      error: () => this.loadAll() // Rollback on error
    });
  }

  rejectRequest(connectionId: number): void {
    // Optimistic update
    this.state.update(s => ({
      ...s,
      pendingRequests: s.pendingRequests.filter(r => r.id !== connectionId)
    }));

    this.networkService.rejectRequest(connectionId).subscribe({
      error: () => this.loadAll()
    });
  }

  removeConnection(connectionId: number): void {
    // Optimistic update
    this.state.update(s => ({
      ...s,
      connections: s.connections.filter(c => c.id !== connectionId)
    }));

    this.networkService.removeConnection(connectionId).subscribe({
      error: () => this.loadAll()
    });
  }
}
```

### 5.3 Enhanced Auth State Service

```typescript
// src/app/core/state/auth-state.service.ts
import { Injectable, computed, signal, effect } from '@angular/core';
import { AuthResponse } from '../../shared/models/models';

@Injectable({ providedIn: 'root' })
export class AuthStateService {
  private readonly TOKEN_KEY = 'currentUser';

  private readonly state = signal<AuthResponse | null>(this.loadFromStorage());

  // Selectors
  readonly currentUser = computed(() => this.state());
  readonly isAuthenticated = computed(() => this.state() !== null);
  readonly userId = computed(() => this.state()?.id ?? null);
  readonly username = computed(() => this.state()?.username ?? null);
  readonly email = computed(() => this.state()?.email ?? null);
  readonly role = computed(() => this.state()?.role ?? null);

  // Derived state
  readonly isAdmin = computed(() => this.state()?.role === 'ADMIN');
  readonly isBusiness = computed(() => this.state()?.role === 'BUSINESS');
  readonly isCreator = computed(() => this.state()?.role === 'CREATOR');
  readonly isBusinessOrCreator = computed(() =>
    this.isBusiness() || this.isCreator()
  );

  readonly displayName = computed(() => {
    const user = this.state();
    if (!user) return null;
    return user.firstName && user.lastName
      ? `${user.firstName} ${user.lastName}`
      : user.username;
  });

  readonly initials = computed(() => {
    const name = this.displayName();
    if (!name) return '??';
    return name.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2);
  });

  constructor() {
    // Sync to localStorage on state changes
    effect(() => {
      const user = this.state();
      if (user) {
        localStorage.setItem(this.TOKEN_KEY, JSON.stringify(user));
      } else {
        localStorage.removeItem(this.TOKEN_KEY);
      }
    });
  }

  setUser(user: AuthResponse): void {
    this.state.set(user);
  }

  logout(): void {
    this.state.set(null);
  }

  updateProfile(updates: Partial<AuthResponse>): void {
    const current = this.state();
    if (current) {
      this.state.set({ ...current, ...updates });
    }
  }

  private loadFromStorage(): AuthResponse | null {
    const stored = localStorage.getItem(this.TOKEN_KEY);
    return stored ? JSON.parse(stored) : null;
  }
}
```

### 5.4 Notifications State Service

```typescript
// src/app/core/state/notifications-state.service.ts
import { Injectable, computed, signal, inject, DestroyRef } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { interval, switchMap } from 'rxjs';
import { Notification } from '../../shared/models/models';
import { NotificationService } from '../services/notification.service';

interface NotificationsState {
  notifications: Notification[];
  unreadCount: number;
  loading: boolean;
  error: string | null;
}

@Injectable({ providedIn: 'root' })
export class NotificationsStateService {
  private readonly notificationService = inject(NotificationService);
  private readonly destroyRef = inject(DestroyRef);

  private readonly state = signal<NotificationsState>({
    notifications: [],
    unreadCount: 0,
    loading: false,
    error: null
  });

  // Selectors
  readonly notifications = computed(() => this.state().notifications);
  readonly unreadCount = computed(() => this.state().unreadCount);
  readonly loading = computed(() => this.state().loading);

  // Derived state
  readonly hasUnread = computed(() => this.state().unreadCount > 0);

  readonly notificationsByType = computed(() => {
    const notifications = this.state().notifications;
    return {
      likes: notifications.filter(n => n.type === 'LIKE'),
      comments: notifications.filter(n => n.type === 'COMMENT'),
      connections: notifications.filter(n => n.type === 'CONNECTION_REQUEST'),
      mentions: notifications.filter(n => n.type === 'MENTION'),
      other: notifications.filter(n => !['LIKE', 'COMMENT', 'CONNECTION_REQUEST', 'MENTION'].includes(n.type))
    };
  });

  readonly notificationsByDate = computed(() => {
    const notifications = this.state().notifications;
    const today = new Date().toDateString();
    const yesterday = new Date(Date.now() - 86400000).toDateString();

    return {
      today: notifications.filter(n => new Date(n.createdAt).toDateString() === today),
      yesterday: notifications.filter(n => new Date(n.createdAt).toDateString() === yesterday),
      older: notifications.filter(n => {
        const date = new Date(n.createdAt).toDateString();
        return date !== today && date !== yesterday;
      })
    };
  });

  readonly unreadNotifications = computed(() =>
    this.state().notifications.filter(n => !n.read)
  );

  // Actions
  loadNotifications(): void {
    this.state.update(s => ({ ...s, loading: true }));

    this.notificationService.getNotifications().subscribe({
      next: (res) => {
        this.state.update(s => ({
          ...s,
          notifications: res.data,
          loading: false
        }));
      },
      error: (err) => {
        this.state.update(s => ({
          ...s,
          error: err.message,
          loading: false
        }));
      }
    });
  }

  startPolling(): void {
    interval(30000).pipe(
      switchMap(() => this.notificationService.getUnreadCount()),
      takeUntilDestroyed(this.destroyRef)
    ).subscribe(res => {
      this.state.update(s => ({ ...s, unreadCount: res.data }));
    });
  }

  markAsRead(notificationId: number): void {
    // Optimistic update
    this.state.update(s => ({
      ...s,
      notifications: s.notifications.map(n =>
        n.id === notificationId ? { ...n, read: true } : n
      ),
      unreadCount: Math.max(0, s.unreadCount - 1)
    }));

    this.notificationService.markAsRead(notificationId).subscribe();
  }

  markAllAsRead(): void {
    const unreadIds = this.state().notifications
      .filter(n => !n.read)
      .map(n => n.id);

    // Optimistic update
    this.state.update(s => ({
      ...s,
      notifications: s.notifications.map(n => ({ ...n, read: true })),
      unreadCount: 0
    }));

    this.notificationService.markAllAsRead(unreadIds).subscribe();
  }
}
```

### 5.5 Refactored Feed Component

```typescript
// src/app/features/feed-page/feed-page.component.ts (REFACTORED)
import { Component, OnInit, inject } from '@angular/core';
import { FeedStateService } from '../../core/state/feed-state.service';
import { AuthStateService } from '../../core/state/auth-state.service';

@Component({
  selector: 'app-feed-page',
  template: `
    <div class="feed-container">
      <!-- Create Post -->
      <app-create-post
        *ngIf="authState.isAuthenticated()"
        (postCreated)="onPostCreated($event)">
      </app-create-post>

      <!-- Trending Hashtags (derived state) -->
      <div class="trending-sidebar">
        <h3>Trending</h3>
        <ul>
          @for (hashtag of feedState.trendingHashtags(); track hashtag.tag) {
            <li>
              <a (click)="filterByHashtag(hashtag.tag)">
                #{{ hashtag.tag }} ({{ hashtag.count }})
              </a>
            </li>
          }
        </ul>
      </div>

      <!-- Feed Stats (derived state) -->
      <div class="feed-stats">
        <span>{{ feedState.postCount() }} posts</span>
        <span>{{ feedState.likeStats().totalLikes }} total likes</span>
      </div>

      <!-- Loading State -->
      <div *ngIf="feedState.loading()" class="loading">
        Loading posts...
      </div>

      <!-- Error State -->
      <div *ngIf="feedState.error()" class="error">
        {{ feedState.error() }}
        <button (click)="feedState.refresh()">Retry</button>
      </div>

      <!-- Posts List -->
      @for (post of feedState.posts(); track post.id) {
        <app-post-card
          [post]="post"
          (like)="onLike(post.id)"
          (delete)="onDelete(post.id)">
        </app-post-card>
      }

      <!-- Load More -->
      <button
        *ngIf="feedState.hasMore() && !feedState.loading()"
        (click)="feedState.loadMore()">
        Load More
      </button>
    </div>
  `
})
export class FeedPageComponent implements OnInit {
  readonly feedState = inject(FeedStateService);
  readonly authState = inject(AuthStateService);

  ngOnInit(): void {
    this.feedState.loadFeed();
  }

  onPostCreated(post: Post): void {
    this.feedState.addPost(post);
  }

  onLike(postId: number): void {
    this.feedState.likePost(postId);
  }

  onDelete(postId: number): void {
    this.feedState.removePost(postId);
  }

  filterByHashtag(hashtag: string): void {
    // Use derived state selector
    const posts = this.feedState.postsWithHashtag()(hashtag);
    // Navigate or filter UI
  }
}
```

---

## 6. Implementation Guide

### 6.1 File Structure for State Management

```
src/app/
├── core/
│   ├── state/                          # NEW: Centralized state
│   │   ├── auth-state.service.ts
│   │   ├── feed-state.service.ts
│   │   ├── connections-state.service.ts
│   │   ├── notifications-state.service.ts
│   │   ├── profile-state.service.ts
│   │   └── index.ts                    # Barrel export
│   ├── services/                       # API services (keep)
│   │   ├── auth.service.ts             # Refactor to use AuthStateService
│   │   ├── post.service.ts             # Keep as API layer
│   │   ├── network.service.ts          # Keep as API layer
│   │   └── notification.service.ts     # Refactor to use NotificationsStateService
│   └── ...
└── ...
```

### 6.2 State Service Pattern Template

```typescript
// Template for new state services
import { Injectable, computed, signal } from '@angular/core';

interface FeatureState {
  data: DataType[];
  selectedId: number | null;
  loading: boolean;
  error: string | null;
}

@Injectable({ providedIn: 'root' })
export class FeatureStateService {
  // Base state
  private readonly state = signal<FeatureState>({
    data: [],
    selectedId: null,
    loading: false,
    error: null
  });

  // Selectors
  readonly data = computed(() => this.state().data);
  readonly selectedId = computed(() => this.state().selectedId);
  readonly loading = computed(() => this.state().loading);
  readonly error = computed(() => this.state().error);

  // Derived state
  readonly dataCount = computed(() => this.state().data.length);
  readonly selectedItem = computed(() =>
    this.state().data.find(d => d.id === this.state().selectedId)
  );
  readonly hasError = computed(() => this.state().error !== null);
  readonly isEmpty = computed(() => this.state().data.length === 0);

  // Actions
  setData(data: DataType[]): void {
    this.state.update(s => ({ ...s, data, error: null }));
  }

  select(id: number): void {
    this.state.update(s => ({ ...s, selectedId: id }));
  }

  setLoading(loading: boolean): void {
    this.state.update(s => ({ ...s, loading }));
  }

  setError(error: string): void {
    this.state.update(s => ({ ...s, error, loading: false }));
  }

  reset(): void {
    this.state.set({
      data: [],
      selectedId: null,
      loading: false,
      error: null
    });
  }
}
```

---

## 7. Migration Strategy

### Phase 1: Foundation (Week 1)
1. Create `core/state/` directory structure
2. Implement `AuthStateService` using signals
3. Refactor `AuthService` to delegate to `AuthStateService`
4. Update guards to use new state service
5. Add unit tests

### Phase 2: Feed Feature (Week 2)
1. Implement `FeedStateService` with derived state
2. Add `PostService.getFeed()` method (remove HTTP from component)
3. Refactor `FeedPageComponent` to use state service
4. Implement optimistic updates for likes
5. Add trending hashtags derived state

### Phase 3: Connections Feature (Week 3)
1. Implement `ConnectionsStateService`
2. Refactor `ConnectionsComponent`
3. Add `forkJoin` for coordinated loading
4. Implement optimistic updates for requests
5. Remove direct localStorage access

### Phase 4: Notifications & Profile (Week 4)
1. Implement `NotificationsStateService`
2. Implement `ProfileStateService`
3. Fix `ProfileViewComponent` (remove mock data)
4. Add proper unsubscription patterns
5. Performance testing

### Migration Checklist

- [ ] Create state service directory structure
- [ ] Implement AuthStateService with signals
- [ ] Implement FeedStateService with derived state
- [ ] Implement ConnectionsStateService with derived state
- [ ] Implement NotificationsStateService with derived state
- [ ] Implement ProfileStateService
- [ ] Refactor FeedPageComponent
- [ ] Refactor ConnectionsComponent
- [ ] Refactor NotificationListComponent
- [ ] Refactor ProfileViewComponent (fix mock data)
- [ ] Refactor ProfileEditComponent
- [ ] Remove direct localStorage access from components
- [ ] Remove direct HTTP calls from components
- [ ] Add takeUntilDestroyed to all subscriptions
- [ ] Implement optimistic updates
- [ ] Add comprehensive unit tests
- [ ] Performance testing

---

## Appendix A: Current vs. Recommended Architecture

### Current Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        Components                            │
├──────────┬──────────┬────────────┬────────────┬─────────────┤
│   Feed   │Connections│ Profile   │Notifications│   Navbar   │
│ [posts]  │[connections]│[profile]│[notifications]│[unread]  │
│ +HTTP!   │+localStorage│         │             │            │
└────┬─────┴─────┬────┴─────┬─────┴──────┬──────┴─────┬──────┘
     │           │          │            │            │
     │           ▼          ▼            ▼            ▼
     │    ┌─────────────────────────────────────────────────┐
     │    │              API Services                        │
     │    │  (No state, just HTTP calls)                     │
     │    └─────────────────────────────────────────────────┘
     │                        │
     └────────────────────────┼────── (bypasses services!)
                              ▼
                        ┌───────────┐
                        │  Backend  │
                        └───────────┘

❌ Feed component makes direct HTTP calls
❌ Connections reads localStorage directly
❌ No shared state between features
❌ Each component manages own loading/error state
```

### Recommended Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Components (Presentational)               │
│           (Use signals/computed, minimal local state)        │
├──────────┬──────────┬────────────┬────────────┬─────────────┤
│   Feed   │Connections│ Profile   │Notifications│   Navbar   │
└────┬─────┴─────┬────┴─────┬─────┴──────┬──────┴─────┬──────┘
     │           │          │            │            │
     └───────────┴──────────┼────────────┴────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    State Services                            │
│    (Signals + Computed + Optimistic Updates + Caching)       │
├──────────┬──────────┬────────────┬────────────┬─────────────┤
│FeedState │ConnectionsState│ProfileState│NotificationsState│AuthState│
└────┬─────┴─────┬────┴─────┬─────┴──────┬──────┴─────┬──────┘
     │           │          │            │            │
     └───────────┴──────────┼────────────┴────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                      API Services                            │
│              (HTTP calls only, no state)                     │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
                      ┌───────────┐
                      │  Backend  │
                      └───────────┘

✅ Single source of truth per feature
✅ Derived state with signals/computed
✅ Optimistic updates
✅ Proper separation of concerns
```

---

## Appendix B: Key Files Reference

| Purpose | Current File | Status |
|---------|-------------|--------|
| Auth State | `core/services/auth.service.ts` | Needs refactoring to signals |
| Notification State | `core/services/notification.service.ts` | Needs enhancement |
| Post API | `core/services/post.service.ts` | Keep as API layer |
| Network API | `core/services/network.service.ts` | Keep as API layer |
| Feed Component | `features/feed-page/feed-page.component.ts` | Needs refactoring |
| Connections Component | `features/network/connections/connections.component.ts` | Needs refactoring |
| Profile View | `features/users/profile-view/profile-view.component.ts` | Has mock data - fix |
| Profile Edit | `features/users/profile-edit/profile-edit.component.ts` | Needs refactoring |
| Notifications List | `features/notifications/notification-list/notification-list.component.ts` | Needs refactoring |
| Models | `shared/models/models.ts` | Good - keep |

---

## Appendix C: Code Quality Issues Found

| Issue | Location | Severity |
|-------|----------|----------|
| Direct HTTP in component | `feed-page.component.ts` | High |
| Direct localStorage access | `connections.component.ts` | High |
| Hardcoded mock data | `profile-view.component.ts` | High |
| No unsubscribe patterns | All components | Medium |
| Direct array mutations | `feed-page.component.ts` | Medium |
| Multiple uncoordinated requests | `connections.component.ts` | Medium |
| No error handling UI | Various | Low |

---

*Document generated as part of code review for RevConnect frontend.*
