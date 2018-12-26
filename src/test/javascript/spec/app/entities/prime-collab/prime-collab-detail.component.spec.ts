/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { PrimeCollabDetailComponent } from '../../../../../../main/webapp/app/entities/prime-collab/prime-collab-detail.component';
import { PrimeCollabService } from '../../../../../../main/webapp/app/entities/prime-collab/prime-collab.service';
import { PrimeCollab } from '../../../../../../main/webapp/app/entities/prime-collab/prime-collab.model';

describe('Component Tests', () => {

    describe('PrimeCollab Management Detail Component', () => {
        let comp: PrimeCollabDetailComponent;
        let fixture: ComponentFixture<PrimeCollabDetailComponent>;
        let service: PrimeCollabService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [PrimeCollabDetailComponent],
                providers: [
                    PrimeCollabService
                ]
            })
            .overrideTemplate(PrimeCollabDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrimeCollabDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrimeCollabService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PrimeCollab(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.primeCollab).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
