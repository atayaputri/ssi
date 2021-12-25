import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBNEGComponent } from '../list/tbneg.component';
import { TBNEGDetailComponent } from '../detail/tbneg-detail.component';
import { TBNEGUpdateComponent } from '../update/tbneg-update.component';
import { TBNEGRoutingResolveService } from './tbneg-routing-resolve.service';

const tBNEGRoute: Routes = [
  {
    path: '',
    component: TBNEGComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':negcod/view',
    component: TBNEGDetailComponent,
    resolve: {
      tBNEG: TBNEGRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBNEGUpdateComponent,
    resolve: {
      tBNEG: TBNEGRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':negcod/edit',
    component: TBNEGUpdateComponent,
    resolve: {
      tBNEG: TBNEGRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBNEGRoute)],
  exports: [RouterModule],
})
export class TBNEGRoutingModule {}
