import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBCOMS } from '../tbcoms.model';
import { TBCOMSService } from '../service/tbcoms.service';

@Component({
  templateUrl: './tbcoms-delete-dialog.component.html',
})
export class TBCOMSDeleteDialogComponent {
  tBCOMS?: ITBCOMS;

  constructor(protected tBCOMSService: TBCOMSService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tBCOMSService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
