import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LibrarySongDetail, SongLibraryService } from 'src/app/core/services/song-library.service';

@Component({
  selector: 'app-song-detail',
  templateUrl: './song-detail.component.html',
  styleUrls: ['./song-detail.component.css']
})
export class SongDetailComponent implements OnInit {
  song: LibrarySongDetail | null = null;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private songLibraryService: SongLibraryService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.loading = false;
      return;
    }

    this.songLibraryService.getPublicSongById(id).subscribe({
      next: (song) => {
        this.song = song;
        this.loading = false;
      },
      error: () => {
        this.songLibraryService.getPublicSongs().subscribe({
          next: (songs) => {
            const match = songs.find((song) => song.id === id);
            this.song = match
              ? {
                  id: match.id,
                  title: match.title,
                  artist: match.artist,
                  albumName: match.album ?? null,
                  genre: match.genre ?? null,
                  duration: null,
                  createdAt: match.releaseDate ?? null,
                }
              : null;
            this.loading = false;
          },
          error: () => {
            this.loading = false;
          },
        });
      }
    });
  }
}
