import { Component, OnInit } from '@angular/core';
import { FavoriteService } from 'src/app/core/services/favorite.service';
import { SongLibraryService } from 'src/app/core/services/song-library.service';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {

  favorites: Array<{ id: number; title: string; artist: string }> = [];

  constructor(
    private favoriteService: FavoriteService,
    private songLibraryService: SongLibraryService
  ) {}

  ngOnInit(): void {
    this.loadFavorites();
  }

  loadFavorites() {
    this.songLibraryService.getPublicSongs().subscribe((songs) => {
      const songMap = new Map(songs.map((song) => [song.id, song]));
      this.favoriteService.get().subscribe((songIds) => {
        this.favorites = songIds
          .map((songId) => songMap.get(songId))
          .filter((song): song is { id: number; title: string; artist: string } => !!song);
      });
    });
  }

  remove(songId: number) {
    const previousFavorites = [...this.favorites];
    this.favorites = this.favorites.filter(song => song.id !== songId);

    this.favoriteService.remove(songId).subscribe({
      next: () => {},
      error: (err) => {
        console.error('Failed to remove favorite', err);
        this.favorites = previousFavorites;
        this.loadFavorites();
      }
    });
  }
}
