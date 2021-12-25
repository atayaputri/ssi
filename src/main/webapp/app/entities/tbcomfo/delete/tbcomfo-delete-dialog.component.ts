import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBCOMFO } from '../tbcomfo.model';
import { TBCOMFOService } from '../service/tbcomfo.service';

@Component({
  templateUrl: './tbcomfo-delete-dialog.component.html',
})
export class TBCOMFODeleteDialogComponent {
  tBCOMFO?: ITBCOMFO;

  constructor(protected tBCOMFOService: TBCOMFOService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tBCOMFOService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
