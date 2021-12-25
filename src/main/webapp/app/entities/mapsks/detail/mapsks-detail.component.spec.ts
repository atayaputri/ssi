import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MAPSKSDetailComponent } from './mapsks-detail.component';

describe('MAPSKS Management Detail Component', () => {
  let comp: MAPSKSDetailComponent;
  let fixture: ComponentFixture<MAPSKSDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MAPSKSDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mAPSKS: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MAPSKSDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MAPSKSDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mAPSKS on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mAPSKS).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
