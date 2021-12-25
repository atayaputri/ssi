import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMAPSKS } from '../mapsks.model';
import { MAPSKSService } from '../service/mapsks.service';

@Component({
  templateUrl: './mapsks-delete-dialog.component.html',
})
export class MAPSKSDeleteDialogComponent {
  mAPSKS?: IMAPSKS;

  constructor(protected mAPSKSService: MAPSKSService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mAPSKSService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
