import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBNEGComponent } from './list/tbneg.component';
import { TBNEGDetailComponent } from './detail/tbneg-detail.component';
import { TBNEGUpdateComponent } from './update/tbneg-update.component';
import { TBNEGDeleteDialogComponent } from './delete/tbneg-delete-dialog.component';
import { TBNEGRoutingModule } from './route/tbneg-routing.module';

@NgModule({
  imports: [SharedModule, TBNEGRoutingModule],
  declarations: [TBNEGComponent, TBNEGDetailComponent, TBNEGUpdateComponent, TBNEGDeleteDialogComponent],
  entryComponents: [TBNEGDeleteDialogComponent],
})
export class TBNEGModule {}
