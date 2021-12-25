jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TABJTRXService } from '../service/tabjtrx.service';

import { TABJTRXDeleteDialogComponent } from './tabjtrx-delete-dialog.component';

describe('TABJTRX Management Delete Component', () => {
  let comp: TABJTRXDeleteDialogComponent;
  let fixture: ComponentFixture<TABJTRXDeleteDialogComponent>;
  let service: TABJTRXService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TABJTRXDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(TABJTRXDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TABJTRXDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TABJTRXService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
