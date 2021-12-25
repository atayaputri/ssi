import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBCOMSComponent } from './list/tbcoms.component';
import { TBCOMSDetailComponent } from './detail/tbcoms-detail.component';
import { TBCOMSUpdateComponent } from './update/tbcoms-update.component';
import { TBCOMSDeleteDialogComponent } from './delete/tbcoms-delete-dialog.component';
import { TBCOMSRoutingModule } from './route/tbcoms-routing.module';

@NgModule({
  imports: [SharedModule, TBCOMSRoutingModule],
  declarations: [TBCOMSComponent, TBCOMSDetailComponent, TBCOMSUpdateComponent, TBCOMSDeleteDialogComponent],
  entryComponents: [TBCOMSDeleteDialogComponent],
})
export class TBCOMSModule {}
