import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TABFEEComponent } from './list/tabfee.component';
import { TABFEEDetailComponent } from './detail/tabfee-detail.component';
import { TABFEEUpdateComponent } from './update/tabfee-update.component';
import { TABFEEDeleteDialogComponent } from './delete/tabfee-delete-dialog.component';
import { TABFEERoutingModule } from './route/tabfee-routing.module';

@NgModule({
  imports: [SharedModule, TABFEERoutingModule],
  declarations: [TABFEEComponent, TABFEEDetailComponent, TABFEEUpdateComponent, TABFEEDeleteDialogComponent],
  entryComponents: [TABFEEDeleteDialogComponent],
})
export class TABFEEModule {}
