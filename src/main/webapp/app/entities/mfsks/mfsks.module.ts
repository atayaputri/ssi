import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MFSKSComponent } from './list/mfsks.component';
import { MFSKSDetailComponent } from './detail/mfsks-detail.component';
import { MFSKSUpdateComponent } from './update/mfsks-update.component';
import { MFSKSDeleteDialogComponent } from './delete/mfsks-delete-dialog.component';
import { MFSKSRoutingModule } from './route/mfsks-routing.module';

@NgModule({
  imports: [SharedModule, MFSKSRoutingModule],
  declarations: [MFSKSComponent, MFSKSDetailComponent, MFSKSUpdateComponent, MFSKSDeleteDialogComponent],
  entryComponents: [MFSKSDeleteDialogComponent],
})
export class MFSKSModule {}
