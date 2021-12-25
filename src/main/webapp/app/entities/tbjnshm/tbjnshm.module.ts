import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBJNSHMComponent } from './list/tbjnshm.component';
import { TBJNSHMDetailComponent } from './detail/tbjnshm-detail.component';
import { TBJNSHMUpdateComponent } from './update/tbjnshm-update.component';
import { TBJNSHMDeleteDialogComponent } from './delete/tbjnshm-delete-dialog.component';
import { TBJNSHMRoutingModule } from './route/tbjnshm-routing.module';

@NgModule({
  imports: [SharedModule, TBJNSHMRoutingModule],
  declarations: [TBJNSHMComponent, TBJNSHMDetailComponent, TBJNSHMUpdateComponent, TBJNSHMDeleteDialogComponent],
  entryComponents: [TBJNSHMDeleteDialogComponent],
})
export class TBJNSHMModule {}
