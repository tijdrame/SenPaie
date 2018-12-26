/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { PrimeCollabComponent } from '../../../../../../main/webapp/app/entities/prime-collab/prime-collab.component';
import { PrimeCollabService } from '../../../../../../main/webapp/app/entities/prime-collab/prime-collab.service';
import { PrimeCollab } from '../../../../../../main/webapp/app/entities/prime-collab/prime-collab.model';

describe('Component Tests', () => {

    describe('PrimeCollab Management Component', () => {
        let comp: PrimeCollabComponent;
        let fixture: ComponentFixture<PrimeCollabComponent>;
        let service: PrimeCollabService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [PrimeCollabComponent],
                providers: [
                    PrimeCollabService
                ]
            })
            .overrideTemplate(PrimeCollabComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrimeCollabComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrimeCollabService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PrimeCollab(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.primeCollabs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
