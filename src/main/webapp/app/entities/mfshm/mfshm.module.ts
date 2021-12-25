import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MFSHMComponent } from './list/mfshm.component';
import { MFSHMDetailComponent } from './detail/mfshm-detail.component';
import { MFSHMUpdateComponent } from './update/mfshm-update.component';
import { MFSHMDeleteDialogComponent } from './delete/mfshm-delete-dialog.component';
import { MFSHMRoutingModule } from './route/mfshm-routing.module';

@NgModule({
  imports: [SharedModule, MFSHMRoutingModule],
  declarations: [MFSHMComponent, MFSHMDetailComponent, MFSHMUpdateComponent, MFSHMDeleteDialogComponent],
  entryComponents: [MFSHMDeleteDialogComponent],
})
export class MFSHMModule {}
