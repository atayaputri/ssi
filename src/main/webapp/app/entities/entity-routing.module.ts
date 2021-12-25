import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'mfsks',
        data: { pageTitle: 'ssiApp.mFSKS.home.title' },
        loadChildren: () => import('./mfsks/mfsks.module').then(m => m.MFSKSModule),
      },
      {
        path: 'mfshm',
        data: { pageTitle: 'ssiApp.mFSHM.home.title' },
        loadChildren: () => import('./mfshm/mfshm.module').then(m => m.MFSHMModule),
      },
      {
        path: 'mfhdr',
        data: { pageTitle: 'ssiApp.mFHDR.home.title' },
        loadChildren: () => import('./mfhdr/mfhdr.module').then(m => m.MFHDRModule),
      },
      {
        path: 'mapsks',
        data: { pageTitle: 'ssiApp.mAPSKS.home.title' },
        loadChildren: () => import('./mapsks/mapsks.module').then(m => m.MAPSKSModule),
      },
      {
        path: 'tbbroker',
        data: { pageTitle: 'ssiApp.tBBROKER.home.title' },
        loadChildren: () => import('./tbbroker/tbbroker.module').then(m => m.TBBROKERModule),
      },
      {
        path: 'tbcoms',
        data: { pageTitle: 'ssiApp.tBCOMS.home.title' },
        loadChildren: () => import('./tbcoms/tbcoms.module').then(m => m.TBCOMSModule),
      },
      {
        path: 'tbkab',
        data: { pageTitle: 'ssiApp.tBKAB.home.title' },
        loadChildren: () => import('./tbkab/tbkab.module').then(m => m.TBKABModule),
      },
      {
        path: 'tbprov',
        data: { pageTitle: 'ssiApp.tBPROV.home.title' },
        loadChildren: () => import('./tbprov/tbprov.module').then(m => m.TBPROVModule),
      },
      {
        path: 'tbneg',
        data: { pageTitle: 'ssiApp.tBNEG.home.title' },
        loadChildren: () => import('./tbneg/tbneg.module').then(m => m.TBNEGModule),
      },
      {
        path: 'tbjnps',
        data: { pageTitle: 'ssiApp.tBJNPS.home.title' },
        loadChildren: () => import('./tbjnps/tbjnps.module').then(m => m.TBJNPSModule),
      },
      {
        path: 'tbjnshm',
        data: { pageTitle: 'ssiApp.tBJNSHM.home.title' },
        loadChildren: () => import('./tbjnshm/tbjnshm.module').then(m => m.TBJNSHMModule),
      },
      {
        path: 'tbtyps',
        data: { pageTitle: 'ssiApp.tBTYPS.home.title' },
        loadChildren: () => import('./tbtyps/tbtyps.module').then(m => m.TBTYPSModule),
      },
      {
        path: 'tbhdr',
        data: { pageTitle: 'ssiApp.tBHDR.home.title' },
        loadChildren: () => import('./tbhdr/tbhdr.module').then(m => m.TBHDRModule),
      },
      {
        path: 'tbpart',
        data: { pageTitle: 'ssiApp.tBPART.home.title' },
        loadChildren: () => import('./tbpart/tbpart.module').then(m => m.TBPARTModule),
      },
      {
        path: 'tbcomfo',
        data: { pageTitle: 'ssiApp.tBCOMFO.home.title' },
        loadChildren: () => import('./tbcomfo/tbcomfo.module').then(m => m.TBCOMFOModule),
      },
      {
        path: 'tabfee',
        data: { pageTitle: 'ssiApp.tABFEE.home.title' },
        loadChildren: () => import('./tabfee/tabfee.module').then(m => m.TABFEEModule),
      },
      {
        path: 'tabjtrx',
        data: { pageTitle: 'ssiApp.tABJTRX.home.title' },
        loadChildren: () => import('./tabjtrx/tabjtrx.module').then(m => m.TABJTRXModule),
      },
      {
        path: 'listemt',
        data: { pageTitle: 'ssiApp.lISTEMT.home.title' },
        loadChildren: () => import('./listemt/listemt.module').then(m => m.LISTEMTModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
