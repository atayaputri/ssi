import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITABFEE } from '../tabfee.model';
import { TABFEEService } from '../service/tabfee.service';

@Component({
  templateUrl: './tabfee-delete-dialog.component.html',
})
export class TABFEEDeleteDialogComponent {
  tABFEE?: ITABFEE;

  constructor(protected tABFEEService: TABFEEService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tABFEEService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
