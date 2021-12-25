import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TBKABComponent } from './list/tbkab.component';
import { TBKABDetailComponent } from './detail/tbkab-detail.component';
import { TBKABUpdateComponent } from './update/tbkab-update.component';
import { TBKABDeleteDialogComponent } from './delete/tbkab-delete-dialog.component';
import { TBKABRoutingModule } from './route/tbkab-routing.module';

@NgModule({
  imports: [SharedModule, TBKABRoutingModule],
  declarations: [TBKABComponent, TBKABDetailComponent, TBKABUpdateComponent, TBKABDeleteDialogComponent],
  entryComponents: [TBKABDeleteDialogComponent],
})
export class TBKABModule {}
