/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { AvantageCollabDetailComponent } from '../../../../../../main/webapp/app/entities/avantage-collab/avantage-collab-detail.component';
import { AvantageCollabService } from '../../../../../../main/webapp/app/entities/avantage-collab/avantage-collab.service';
import { AvantageCollab } from '../../../../../../main/webapp/app/entities/avantage-collab/avantage-collab.model';

describe('Component Tests', () => {

    describe('AvantageCollab Management Detail Component', () => {
        let comp: AvantageCollabDetailComponent;
        let fixture: ComponentFixture<AvantageCollabDetailComponent>;
        let service: AvantageCollabService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [AvantageCollabDetailComponent],
                providers: [
                    AvantageCollabService
                ]
            })
            .overrideTemplate(AvantageCollabDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AvantageCollabDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AvantageCollabService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AvantageCollab(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.avantageCollab).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
