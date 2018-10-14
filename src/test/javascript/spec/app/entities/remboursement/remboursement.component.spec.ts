/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { RemboursementComponent } from '../../../../../../main/webapp/app/entities/remboursement/remboursement.component';
import { RemboursementService } from '../../../../../../main/webapp/app/entities/remboursement/remboursement.service';
import { Remboursement } from '../../../../../../main/webapp/app/entities/remboursement/remboursement.model';

describe('Component Tests', () => {

    describe('Remboursement Management Component', () => {
        let comp: RemboursementComponent;
        let fixture: ComponentFixture<RemboursementComponent>;
        let service: RemboursementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [RemboursementComponent],
                providers: [
                    RemboursementService
                ]
            })
            .overrideTemplate(RemboursementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RemboursementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RemboursementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Remboursement(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.remboursements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
