import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HistoryRoutingModule } from './history-routing.module';
import { ListeningHistoryComponent } from './listening-history.component';


@NgModule({
  declarations: [ListeningHistoryComponent],
  imports: [
    CommonModule,
    HistoryRoutingModule
  ]
})
export class HistoryModule { }
