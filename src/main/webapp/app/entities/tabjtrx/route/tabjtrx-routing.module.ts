import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TABJTRXComponent } from '../list/tabjtrx.component';
import { TABJTRXDetailComponent } from '../detail/tabjtrx-detail.component';
import { TABJTRXUpdateComponent } from '../update/tabjtrx-update.component';
import { TABJTRXRoutingResolveService } from './tabjtrx-routing-resolve.service';

const tABJTRXRoute: Routes = [
  {
    path: '',
    component: TABJTRXComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':jtjntx/view',
    component: TABJTRXDetailComponent,
    resolve: {
      tABJTRX: TABJTRXRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TABJTRXUpdateComponent,
    resolve: {
      tABJTRX: TABJTRXRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':jtjntx/edit',
    component: TABJTRXUpdateComponent,
    resolve: {
      tABJTRX: TABJTRXRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tABJTRXRoute)],
  exports: [RouterModule],
})
export class TABJTRXRoutingModule {}
