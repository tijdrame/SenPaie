/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { AvantageCollabComponent } from '../../../../../../main/webapp/app/entities/avantage-collab/avantage-collab.component';
import { AvantageCollabService } from '../../../../../../main/webapp/app/entities/avantage-collab/avantage-collab.service';
import { AvantageCollab } from '../../../../../../main/webapp/app/entities/avantage-collab/avantage-collab.model';

describe('Component Tests', () => {

    describe('AvantageCollab Management Component', () => {
        let comp: AvantageCollabComponent;
        let fixture: ComponentFixture<AvantageCollabComponent>;
        let service: AvantageCollabService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [AvantageCollabComponent],
                providers: [
                    AvantageCollabService
                ]
            })
            .overrideTemplate(AvantageCollabComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AvantageCollabComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AvantageCollabService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AvantageCollab(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.avantageCollabs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
