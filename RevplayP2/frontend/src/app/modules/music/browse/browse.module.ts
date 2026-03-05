import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BrowseRoutingModule } from './browse-routing.module';
import { BrowseComponent } from './browse.component';
import { SongDetailComponent } from './song-detail.component';


@NgModule({
  declarations: [
    BrowseComponent,
    SongDetailComponent
  ],
  imports: [
    CommonModule,
    BrowseRoutingModule
  ]
})
export class BrowseModule { }
