import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TABFEEComponent } from '../list/tabfee.component';
import { TABFEEDetailComponent } from '../detail/tabfee-detail.component';
import { TABFEEUpdateComponent } from '../update/tabfee-update.component';
import { TABFEERoutingResolveService } from './tabfee-routing-resolve.service';

const tABFEERoute: Routes = [
  {
    path: '',
    component: TABFEEComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TABFEEDetailComponent,
    resolve: {
      tABFEE: TABFEERoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TABFEEUpdateComponent,
    resolve: {
      tABFEE: TABFEERoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TABFEEUpdateComponent,
    resolve: {
      tABFEE: TABFEERoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tABFEERoute)],
  exports: [RouterModule],
})
export class TABFEERoutingModule {}
