import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlbumService } from 'src/app/core/services/album.service';
import { SongService } from 'src/app/core/services/song.service';

@Component({
  selector: 'app-manage-albums',
  templateUrl: './manage-albums.component.html',
  styleUrls: ['./manage-albums.component.css']
})
export class ManageAlbumsComponent implements OnInit {

  albumForm!: FormGroup;
  albums: any[] = [];
  songs: any[] = [];

  artistId = Number(localStorage.getItem("artistId"));

  constructor(
    private fb: FormBuilder,
    private albumService: AlbumService,
    private songService: SongService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadAlbums();
    this.loadSongs();
  }

  initForm() {
    this.albumForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      releaseDate: ['', Validators.required]
    });
  }

  loadAlbums() {
    this.albumService.getAlbums(this.artistId)
      .subscribe((res: any) => {
        console.log("Album API Response:", res);
        this.albums = res.data;
      });
  }

  loadSongs() {
    this.songService.getSongs(this.artistId)
      .subscribe((res: any) => {
        this.songs = res.data;
      });
  }

  createAlbum() {
    if (this.albumForm.invalid) return;

    this.albumService.createAlbum(this.artistId, this.albumForm.value)
      .subscribe(() => {
        alert('Album Created Successfully');
        this.albumForm.reset();
        this.loadAlbums();
      });
  }

  addToAlbum(songId: number, albumId: number) {
    this.songService.addSongToAlbum(
      this.artistId,
      songId,
      albumId
    ).subscribe(() => {
      alert("Song added to album successfully");
      this.loadAlbums();
      this.loadSongs();
    });
  }

  removeFromAlbum(songId: number) {
    this.songService.removeSongFromAlbum(
      this.artistId,
      songId
   ).subscribe(() => {
     alert("Song removed from album");
     this.loadAlbums();
     this.loadSongs();
   });
 }


  editingAlbumId: number | null = null;
albumEditForm: any = {
  name: '',
  description: '',
  releaseDate: ''
};

startAlbumEdit(album: any) {
  this.editingAlbumId = album.id;

  this.albumEditForm = {
    name: album.name,
    description: album.description,
    releaseDate: album.releaseDate
  };
}

updateAlbum(albumId: number) {

  this.albumService.updateAlbum(
    this.artistId,
    albumId,
    this.albumEditForm
  ).subscribe(() => {

    alert("Album updated successfully");

    this.editingAlbumId = null;
    this.loadAlbums();
  });
}


  deleteAlbum(albumId: number) {
    if (!confirm('Are you sure to delete this album?')) return;

    this.albumService.deleteAlbum(this.artistId, albumId)
      .subscribe(() => {
        alert('Album Deleted Successfully');
        this.loadAlbums();
      });
  }
}
