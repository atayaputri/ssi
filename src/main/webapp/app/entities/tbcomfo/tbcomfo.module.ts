import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBCOMFOComponent } from './list/tbcomfo.component';
import { TBCOMFODetailComponent } from './detail/tbcomfo-detail.component';
import { TBCOMFOUpdateComponent } from './update/tbcomfo-update.component';
import { TBCOMFODeleteDialogComponent } from './delete/tbcomfo-delete-dialog.component';
import { TBCOMFORoutingModule } from './route/tbcomfo-routing.module';

@NgModule({
  imports: [SharedModule, TBCOMFORoutingModule],
  declarations: [TBCOMFOComponent, TBCOMFODetailComponent, TBCOMFOUpdateComponent, TBCOMFODeleteDialogComponent],
  entryComponents: [TBCOMFODeleteDialogComponent],
})
export class TBCOMFOModule {}
