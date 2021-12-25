import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MFSKSComponent } from '../list/mfsks.component';
import { MFSKSDetailComponent } from '../detail/mfsks-detail.component';
import { MFSKSUpdateComponent } from '../update/mfsks-update.component';
import { MFSKSRoutingResolveService } from './mfsks-routing-resolve.service';

const mFSKSRoute: Routes = [
  {
    path: '',
    component: MFSKSComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':skno/view',
    component: MFSKSDetailComponent,
    resolve: {
      mFSKS: MFSKSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MFSKSUpdateComponent,
    resolve: {
      mFSKS: MFSKSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':skno/edit',
    component: MFSKSUpdateComponent,
    resolve: {
      mFSKS: MFSKSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mFSKSRoute)],
  exports: [RouterModule],
})
export class MFSKSRoutingModule {}
