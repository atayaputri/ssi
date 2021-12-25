import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBPARTComponent } from './list/tbpart.component';
import { TBPARTDetailComponent } from './detail/tbpart-detail.component';
import { TBPARTUpdateComponent } from './update/tbpart-update.component';
import { TBPARTDeleteDialogComponent } from './delete/tbpart-delete-dialog.component';
import { TBPARTRoutingModule } from './route/tbpart-routing.module';

@NgModule({
  imports: [SharedModule, TBPARTRoutingModule],
  declarations: [TBPARTComponent, TBPARTDetailComponent, TBPARTUpdateComponent, TBPARTDeleteDialogComponent],
  entryComponents: [TBPARTDeleteDialogComponent],
})
export class TBPARTModule {}
