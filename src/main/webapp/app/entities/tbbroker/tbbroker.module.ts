import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBBROKERComponent } from './list/tbbroker.component';
import { TBBROKERDetailComponent } from './detail/tbbroker-detail.component';
import { TBBROKERUpdateComponent } from './update/tbbroker-update.component';
import { TBBROKERDeleteDialogComponent } from './delete/tbbroker-delete-dialog.component';
import { TBBROKERRoutingModule } from './route/tbbroker-routing.module';

@NgModule({
  imports: [SharedModule, TBBROKERRoutingModule],
  declarations: [TBBROKERComponent, TBBROKERDetailComponent, TBBROKERUpdateComponent, TBBROKERDeleteDialogComponent],
  entryComponents: [TBBROKERDeleteDialogComponent],
})
export class TBBROKERModule {}
