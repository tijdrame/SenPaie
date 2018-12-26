/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { RecapComponent } from '../../../../../../main/webapp/app/entities/recap/recap.component';
import { RecapService } from '../../../../../../main/webapp/app/entities/recap/recap.service';
import { Recap } from '../../../../../../main/webapp/app/entities/recap/recap.model';

describe('Component Tests', () => {

    describe('Recap Management Component', () => {
        let comp: RecapComponent;
        let fixture: ComponentFixture<RecapComponent>;
        let service: RecapService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [RecapComponent],
                providers: [
                    RecapService
                ]
            })
            .overrideTemplate(RecapComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecapComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecapService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Recap(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.recaps[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
