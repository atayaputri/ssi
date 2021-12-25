import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TABJTRXComponent } from './list/tabjtrx.component';
import { TABJTRXDetailComponent } from './detail/tabjtrx-detail.component';
import { TABJTRXUpdateComponent } from './update/tabjtrx-update.component';
import { TABJTRXDeleteDialogComponent } from './delete/tabjtrx-delete-dialog.component';
import { TABJTRXRoutingModule } from './route/tabjtrx-routing.module';

@NgModule({
  imports: [SharedModule, TABJTRXRoutingModule],
  declarations: [TABJTRXComponent, TABJTRXDetailComponent, TABJTRXUpdateComponent, TABJTRXDeleteDialogComponent],
  entryComponents: [TABJTRXDeleteDialogComponent],
})
export class TABJTRXModule {}
