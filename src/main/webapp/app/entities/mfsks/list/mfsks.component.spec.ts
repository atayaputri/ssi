import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { MFSKSService } from '../service/mfsks.service';

import { MFSKSComponent } from './mfsks.component';

describe('MFSKS Management Component', () => {
  let comp: MFSKSComponent;
  let fixture: ComponentFixture<MFSKSComponent>;
  let service: MFSKSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MFSKSComponent],
    })
      .overrideTemplate(MFSKSComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MFSKSComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MFSKSService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ skno: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.mFSKS?.[0]).toEqual(expect.objectContaining({ skno: 123 }));
  });
});
