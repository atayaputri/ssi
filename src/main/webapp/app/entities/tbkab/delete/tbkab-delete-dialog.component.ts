import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBKAB } from '../tbkab.model';
import { TBKABService } from '../service/tbkab.service';

@Component({
  templateUrl: './tbkab-delete-dialog.component.html',
})
export class TBKABDeleteDialogComponent {
  tBKAB?: ITBKAB;

  constructor(protected tBKABService: TBKABService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tBKABService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
