import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBJNPSComponent } from './list/tbjnps.component';
import { TBJNPSDetailComponent } from './detail/tbjnps-detail.component';
import { TBJNPSUpdateComponent } from './update/tbjnps-update.component';
import { TBJNPSDeleteDialogComponent } from './delete/tbjnps-delete-dialog.component';
import { TBJNPSRoutingModule } from './route/tbjnps-routing.module';

@NgModule({
  imports: [SharedModule, TBJNPSRoutingModule],
  declarations: [TBJNPSComponent, TBJNPSDetailComponent, TBJNPSUpdateComponent, TBJNPSDeleteDialogComponent],
  entryComponents: [TBJNPSDeleteDialogComponent],
})
export class TBJNPSModule {}
