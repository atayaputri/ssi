import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MFHDRComponent } from './list/mfhdr.component';
import { MFHDRDetailComponent } from './detail/mfhdr-detail.component';
import { MFHDRUpdateComponent } from './update/mfhdr-update.component';
import { MFHDRDeleteDialogComponent } from './delete/mfhdr-delete-dialog.component';
import { MFHDRRoutingModule } from './route/mfhdr-routing.module';

@NgModule({
  imports: [SharedModule, MFHDRRoutingModule],
  declarations: [MFHDRComponent, MFHDRDetailComponent, MFHDRUpdateComponent, MFHDRDeleteDialogComponent],
  entryComponents: [MFHDRDeleteDialogComponent],
})
export class MFHDRModule {}
