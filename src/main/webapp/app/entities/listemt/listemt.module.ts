import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LISTEMTComponent } from './list/listemt.component';
import { LISTEMTDetailComponent } from './detail/listemt-detail.component';
import { LISTEMTUpdateComponent } from './update/listemt-update.component';
import { LISTEMTDeleteDialogComponent } from './delete/listemt-delete-dialog.component';
import { LISTEMTRoutingModule } from './route/listemt-routing.module';

@NgModule({
  imports: [SharedModule, LISTEMTRoutingModule],
  declarations: [LISTEMTComponent, LISTEMTDetailComponent, LISTEMTUpdateComponent, LISTEMTDeleteDialogComponent],
  entryComponents: [LISTEMTDeleteDialogComponent],
})
export class LISTEMTModule {}
