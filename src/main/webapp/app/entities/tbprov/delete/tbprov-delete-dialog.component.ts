import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBPROV } from '../tbprov.model';
import { TBPROVService } from '../service/tbprov.service';

@Component({
  templateUrl: './tbprov-delete-dialog.component.html',
})
export class TBPROVDeleteDialogComponent {
  tBPROV?: ITBPROV;

  constructor(protected tBPROVService: TBPROVService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tBPROVService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
