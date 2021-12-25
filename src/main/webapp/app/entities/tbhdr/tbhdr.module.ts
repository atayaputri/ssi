import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBHDRComponent } from './list/tbhdr.component';
import { TBHDRDetailComponent } from './detail/tbhdr-detail.component';
import { TBHDRUpdateComponent } from './update/tbhdr-update.component';
import { TBHDRDeleteDialogComponent } from './delete/tbhdr-delete-dialog.component';
import { TBHDRRoutingModule } from './route/tbhdr-routing.module';

@NgModule({
  imports: [SharedModule, TBHDRRoutingModule],
  declarations: [TBHDRComponent, TBHDRDetailComponent, TBHDRUpdateComponent, TBHDRDeleteDialogComponent],
  entryComponents: [TBHDRDeleteDialogComponent],
})
export class TBHDRModule {}
