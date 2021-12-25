import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBPROVComponent } from './list/tbprov.component';
import { TBPROVDetailComponent } from './detail/tbprov-detail.component';
import { TBPROVUpdateComponent } from './update/tbprov-update.component';
import { TBPROVDeleteDialogComponent } from './delete/tbprov-delete-dialog.component';
import { TBPROVRoutingModule } from './route/tbprov-routing.module';

@NgModule({
  imports: [SharedModule, TBPROVRoutingModule],
  declarations: [TBPROVComponent, TBPROVDetailComponent, TBPROVUpdateComponent, TBPROVDeleteDialogComponent],
  entryComponents: [TBPROVDeleteDialogComponent],
})
export class TBPROVModule {}
