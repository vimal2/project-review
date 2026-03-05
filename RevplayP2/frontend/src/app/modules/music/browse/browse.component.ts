import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { FavoriteService } from 'src/app/core/services/favorite.service';
import { LibrarySong, SongLibraryService } from 'src/app/core/services/song-library.service';

@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css']
})
export class BrowseComponent implements OnInit {

  private allSongs: Array<LibrarySong & { isFavorite: boolean }> = [];
  songs: Array<LibrarySong & { isFavorite: boolean }> = [];
  searchTerm = '';
  artistFilter = '';
  albumFilter = '';
  genreFilter = '';
  releaseDateFilter = '';
  sortBy = '';
  currentPage = 1;
  readonly pageSize = 5;
  processingSongIds = new Set<number>();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private songLibraryService: SongLibraryService,
    private favoriteService: FavoriteService
  ) {}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((params) => {
      this.searchTerm = (params.get('q') || '').trim().toLowerCase();
      this.artistFilter = (params.get('artist') || '').trim().toLowerCase();
      this.albumFilter = (params.get('album') || '').trim().toLowerCase();
      this.genreFilter = (params.get('genre') || '').trim().toLowerCase();
      this.releaseDateFilter = (params.get('releaseDate') || '').trim();
      this.sortBy = (params.get('sort') || '').trim();
      this.currentPage = 1;
      this.applyFilter();
    });
    this.loadSongs();
  }

  loadSongs(): void {
    this.songLibraryService.getPublicSongs().subscribe((songs) => {
      this.allSongs = songs.map((song) => ({ ...song, isFavorite: false }));
      this.applyFilter();
      this.loadFavorites();
    });
  }

  loadFavorites(): void {
    this.favoriteService.get().subscribe((favorites) => {
      const favoriteSet = new Set(favorites);
      this.allSongs = this.allSongs.map(song => ({
        ...song,
        isFavorite: favoriteSet.has(song.id)
      }));
      this.applyFilter();
    });
  }

  private setFavorite(songId: number, favorite: boolean): void {
    this.allSongs = this.allSongs.map(song =>
      song.id === songId ? { ...song, isFavorite: favorite } : song
    );
    this.songs = this.songs.map(song =>
      song.id === songId ? { ...song, isFavorite: favorite } : song
    );
  }

  private applyFilter(): void {
    let filtered = [...this.allSongs];

    if (this.searchTerm) {
      filtered = filtered.filter((song) => {
        const title = song.title?.toLowerCase() || '';
        const artist = song.artist?.toLowerCase() || '';
        return title.includes(this.searchTerm) || artist.includes(this.searchTerm);
      });
    }

    if (this.artistFilter) {
      filtered = filtered.filter((song) =>
        (song.artist?.toLowerCase() || '').includes(this.artistFilter),
      );
    }

    if (this.albumFilter) {
      filtered = filtered.filter((song) =>
        (song.album?.toLowerCase() || '').includes(this.albumFilter),
      );
    }

    if (this.genreFilter) {
      filtered = filtered.filter((song) =>
        (song.genre?.toLowerCase() || '').includes(this.genreFilter),
      );
    }

    if (this.releaseDateFilter) {
      filtered = filtered.filter((song) =>
        (song.releaseDate || '').slice(0, 10) === this.releaseDateFilter,
      );
    }

    if (this.sortBy) {
      filtered = [...filtered].sort((a, b) => {
        const artistA = a.artist?.toLowerCase() || '';
        const artistB = b.artist?.toLowerCase() || '';
        const albumA = a.album?.toLowerCase() || '';
        const albumB = b.album?.toLowerCase() || '';
        const genreA = a.genre?.toLowerCase() || '';
        const genreB = b.genre?.toLowerCase() || '';
        const releaseA = a.releaseDate ? new Date(a.releaseDate).getTime() : 0;
        const releaseB = b.releaseDate ? new Date(b.releaseDate).getTime() : 0;

        switch (this.sortBy) {
          case 'releaseDateAsc':
            return releaseA - releaseB;
          case 'releaseDateDesc':
            return releaseB - releaseA;
          case 'artistAsc':
            return artistA.localeCompare(artistB);
          case 'albumAsc':
            return albumA.localeCompare(albumB);
          case 'genreAsc':
            return genreA.localeCompare(genreB);
          default:
            return 0;
        }
      });
    }

    this.songs = filtered;
    const totalPages = this.totalPages;
    if (this.currentPage > totalPages) {
      this.currentPage = totalPages;
    }
  }

  get pagedSongs(): Array<LibrarySong & { isFavorite: boolean }> {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.songs.slice(start, start + this.pageSize);
  }

  get totalPages(): number {
    return Math.max(1, Math.ceil(this.songs.length / this.pageSize));
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage -= 1;
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage += 1;
    }
  }

  toggleFavorite(song: LibrarySong & { isFavorite: boolean }): void {
    const songId = song.id;
    if (this.processingSongIds.has(songId)) {
      return;
    }

    const currentlyFavorite = song.isFavorite;
    this.processingSongIds.add(songId);

    if (currentlyFavorite) {
      this.setFavorite(songId, false);
      this.favoriteService.remove(songId).subscribe({
        next: () => {
          this.processingSongIds.delete(songId);
        },
        error: (err) => {
          console.error('Failed to remove favorite', err);
          this.processingSongIds.delete(songId);
        }
      });
      return;
    }

    this.setFavorite(songId, true);
    this.favoriteService.add(songId).subscribe({
      next: () => {
        this.processingSongIds.delete(songId);
      },
      error: (err) => {
        console.error('Failed to add favorite', err);
        this.processingSongIds.delete(songId);
      }
    });
  }

  viewSongDetails(songId: number): void {
    this.router.navigate(['/browse', songId], {
      queryParamsHandling: 'preserve'
    });
  }

  playSong(songId: number): void {
    this.router.navigate(['/player'], {
      queryParams: { songId }
    });
  }
}
