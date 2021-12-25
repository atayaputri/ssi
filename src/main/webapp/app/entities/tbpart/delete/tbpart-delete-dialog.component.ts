import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBPART } from '../tbpart.model';
import { TBPARTService } from '../service/tbpart.service';

@Component({
  templateUrl: './tbpart-delete-dialog.component.html',
})
export class TBPARTDeleteDialogComponent {
  tBPART?: ITBPART;

  constructor(protected tBPARTService: TBPARTService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tBPARTService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
