import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBKABComponent } from '../list/tbkab.component';
import { TBKABDetailComponent } from '../detail/tbkab-detail.component';
import { TBKABUpdateComponent } from '../update/tbkab-update.component';
import { TBKABRoutingResolveService } from './tbkab-routing-resolve.service';

const tBKABRoute: Routes = [
  {
    path: '',
    component: TBKABComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':kabcod/view',
    component: TBKABDetailComponent,
    resolve: {
      tBKAB: TBKABRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBKABUpdateComponent,
    resolve: {
      tBKAB: TBKABRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':kabcod/edit',
    component: TBKABUpdateComponent,
    resolve: {
      tBKAB: TBKABRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBKABRoute)],
  exports: [RouterModule],
})
export class TBKABRoutingModule {}
