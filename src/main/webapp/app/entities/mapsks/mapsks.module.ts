import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MAPSKSComponent } from './list/mapsks.component';
import { MAPSKSDetailComponent } from './detail/mapsks-detail.component';
import { MAPSKSUpdateComponent } from './update/mapsks-update.component';
import { MAPSKSDeleteDialogComponent } from './delete/mapsks-delete-dialog.component';
import { MAPSKSRoutingModule } from './route/mapsks-routing.module';

@NgModule({
  imports: [SharedModule, MAPSKSRoutingModule],
  declarations: [MAPSKSComponent, MAPSKSDetailComponent, MAPSKSUpdateComponent, MAPSKSDeleteDialogComponent],
  entryComponents: [MAPSKSDeleteDialogComponent],
})
export class MAPSKSModule {}
