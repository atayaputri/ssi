import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBTYPSComponent } from './list/tbtyps.component';
import { TBTYPSDetailComponent } from './detail/tbtyps-detail.component';
import { TBTYPSUpdateComponent } from './update/tbtyps-update.component';
import { TBTYPSDeleteDialogComponent } from './delete/tbtyps-delete-dialog.component';
import { TBTYPSRoutingModule } from './route/tbtyps-routing.module';

@NgModule({
  imports: [SharedModule, TBTYPSRoutingModule],
  declarations: [TBTYPSComponent, TBTYPSDetailComponent, TBTYPSUpdateComponent, TBTYPSDeleteDialogComponent],
  entryComponents: [TBTYPSDeleteDialogComponent],
})
export class TBTYPSModule {}
