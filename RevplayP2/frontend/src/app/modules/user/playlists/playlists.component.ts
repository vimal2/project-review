import { Component, OnInit } from '@angular/core';
import { Playlist, PlaylistService } from 'src/app/core/services/playlist.service';
import { LibrarySong, SongLibraryService } from 'src/app/core/services/song-library.service';

@Component({
  selector: 'app-playlists',
  templateUrl: './playlists.component.html',
  styleUrls: ['./playlists.component.css']
})
export class PlaylistsComponent implements OnInit {

  availableSongs: LibrarySong[] = [];
  playlists: Playlist[] = [];
  newPlaylist = { name: '', description: '' };
  editDrafts: Record<number, { name: string; description: string }> = {};
  songInput: Record<number, number | null> = {};
  processingPlaylistIds = new Set<number>();

  constructor(
    private playlistService: PlaylistService,
    private songLibraryService: SongLibraryService
  ) {}

  ngOnInit(): void {
    this.loadSongs();
  }

  loadSongs(): void {
    this.songLibraryService.getPublicSongs().subscribe((songs) => {
      this.availableSongs = songs;
      this.load();
    });
  }

  load(): void {
    this.playlistService.getAll().subscribe((res: Playlist[]) => {
      this.playlists = res;
      this.playlists.forEach((playlist) => {
        this.editDrafts[playlist.id] = { name: playlist.name, description: playlist.description };
        if (this.songInput[playlist.id] === undefined) {
          this.songInput[playlist.id] = this.availableSongs.length > 0 ? this.availableSongs[0].id : null;
        }
      });
    });
  }

  canCreatePlaylist(): boolean {
    return this.newPlaylist.name.trim().length > 0;
  }

  create(): void {
    const name = this.newPlaylist.name.trim();
    if (!name) {
      return;
    }

    this.playlistService.create({
      name,
      description: this.newPlaylist.description
    })
      .subscribe(() => {
        this.newPlaylist = { name: '', description: '' };
        this.load();
      });
  }

  update(playlistId: number): void {
    const draft = this.editDrafts[playlistId];
    this.playlistService.update(playlistId, draft).subscribe(() => this.load());
  }

  delete(playlistId: number): void {
    this.playlistService.delete(playlistId).subscribe(() => this.load());
  }

  addSong(playlistId: number): void {
    if (this.processingPlaylistIds.has(playlistId)) {
      return;
    }

    const playlist = this.playlists.find(item => item.id === playlistId);
    if (!playlist) {
      return;
    }

    const songId = this.songInput[playlistId];
    if (!songId || !this.availableSongs.some(song => song.id === songId)) {
      return;
    }

    if (!playlist.songIds.includes(songId)) {
      playlist.songIds = [...playlist.songIds, songId];
    }

    this.processingPlaylistIds.add(playlistId);
    this.playlistService.addSong(playlistId, songId).subscribe({
      next: () => {
        this.processingPlaylistIds.delete(playlistId);
      },
      error: (err) => {
        console.error('Failed to add song to playlist', err);
        playlist.songIds = playlist.songIds.filter(id => id !== songId);
        this.processingPlaylistIds.delete(playlistId);
      }
    });
  }

  removeSong(playlistId: number, songId: number): void {
    if (this.processingPlaylistIds.has(playlistId)) {
      return;
    }

    const playlist = this.playlists.find(item => item.id === playlistId);
    if (!playlist) {
      return;
    }

    const previousSongIds = [...playlist.songIds];
    playlist.songIds = playlist.songIds.filter(id => id !== songId);

    this.processingPlaylistIds.add(playlistId);
    this.playlistService.removeSong(playlistId, songId).subscribe({
      next: () => {
        this.processingPlaylistIds.delete(playlistId);
      },
      error: (err) => {
        console.error('Failed to remove song from playlist', err);
        playlist.songIds = previousSongIds;
        this.processingPlaylistIds.delete(playlistId);
      }
    });
  }

  getSongLabel(songId: number): string {
    const song = this.availableSongs.find(item => item.id === songId);
    return song ? `${song.title} - ${song.artist}` : `Unknown Song (${songId})`;
  }
}
