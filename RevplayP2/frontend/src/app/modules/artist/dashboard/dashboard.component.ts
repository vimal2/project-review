import { Component, OnInit } from '@angular/core';
import {
  AnalyticsService,
  Overview,
  SongAnalytics,
  TopListener,
  TrendResponse
} from 'src/app/core/services/analytics.service';

@Component({
  selector: 'app-artist-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  artistId = Number(localStorage.getItem('artistId')) || 1;
  overview?: Overview;
  songs: SongAnalytics[] = [];
  topSongs: SongAnalytics[] = [];
  topListeners: TopListener[] = [];
  trends: TrendResponse[] = [];
  trendType: 'daily' | 'weekly' | 'monthly' = 'monthly';

  constructor(private analyticsService: AnalyticsService) {}

  ngOnInit(): void {
    this.loadOverview();
    this.loadSongs();
    this.loadTopSongs();
    this.loadTrends();
    this.loadTopListeners();
  }

  loadOverview(): void {
    this.analyticsService.getOverview(this.artistId)
      .subscribe(data => this.overview = data);
  }

  loadSongs(): void {
    this.analyticsService.getSongs(this.artistId)
      .subscribe(data => this.songs = data);
  }

  loadTopSongs(): void {
    this.analyticsService.getTopSongs(this.artistId)
      .subscribe(data => this.topSongs = data);
  }

  loadTrends(): void {
    this.analyticsService.getTrends(this.artistId, this.trendType)
      .subscribe(data => this.trends = data);
  }

  onTrendTypeChange(type: 'daily' | 'weekly' | 'monthly'): void {
    this.trendType = type;
    this.loadTrends();
  }

  loadTopListeners(): void {
    this.analyticsService.getTopListeners(this.artistId)
      .subscribe(data => this.topListeners = data);
  }
}
