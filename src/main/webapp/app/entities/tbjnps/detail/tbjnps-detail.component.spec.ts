import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBJNPSDetailComponent } from './tbjnps-detail.component';

describe('TBJNPS Management Detail Component', () => {
  let comp: TBJNPSDetailComponent;
  let fixture: ComponentFixture<TBJNPSDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBJNPSDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBJNPS: { jpscod: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TBJNPSDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBJNPSDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBJNPS on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBJNPS).toEqual(expect.objectContaining({ jpscod: 'ABC' }));
    });
  });
});
