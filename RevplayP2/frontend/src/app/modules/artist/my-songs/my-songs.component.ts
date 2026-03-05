import { Component, OnInit } from '@angular/core';
import { SongService } from 'src/app/core/services/song.service';

@Component({
  selector: 'app-my-songs',
  templateUrl: './my-songs.component.html',
  styleUrls: ['./my-songs.component.css']
})
export class MySongsComponent implements OnInit {

  songs: any[] = [];
  artistId = Number(localStorage.getItem("artistId"));

  constructor(private songService: SongService) {}

  ngOnInit(): void {
    this.loadSongs();
  }

  loadSongs() {
    this.songService.getSongs(this.artistId)
      .subscribe((res: any) => {
        this.songs = res.data;
      });
  }

editingSongId: number | null = null;
editForm: any = {
  title: '',
  genre: '',
  duration: 0,
  visibility: ''
};

startEdit(song: any) {
  this.editingSongId = song.id;

  this.editForm = {
    title: song.title,
    genre: song.genre,
    duration: song.duration,
    visibility: song.visibility
  };
}

cancelEdit() {
  this.editingSongId = null;
}

updateSong(songId: number) {

  this.songService.updateSong(
    this.artistId,
    songId,
    this.editForm
  ).subscribe(() => {

    alert("Song updated successfully");

    this.editingSongId = null;
    this.loadSongs();
  });
}

  deleteSong(songId: number) {
    if (!confirm("Are you sure to delete this song?")) return;

    this.songService.deleteSong(this.artistId, songId)
      .subscribe(() => {
        alert("Song deleted successfully");
        this.loadSongs();
      });
  }

  toggleVisibility(song: any) {

    const newVisibility =
      song.visibility === 'PUBLIC' ? 'UNLISTED' : 'PUBLIC';

    this.songService.updateVisibility(
      this.artistId,
      song.id,
      newVisibility
    ).subscribe(() => {
      song.visibility = newVisibility;
    });
  }
}
