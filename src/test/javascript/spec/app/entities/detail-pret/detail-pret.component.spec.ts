/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { DetailPretComponent } from '../../../../../../main/webapp/app/entities/detail-pret/detail-pret.component';
import { DetailPretService } from '../../../../../../main/webapp/app/entities/detail-pret/detail-pret.service';
import { DetailPret } from '../../../../../../main/webapp/app/entities/detail-pret/detail-pret.model';

describe('Component Tests', () => {

    describe('DetailPret Management Component', () => {
        let comp: DetailPretComponent;
        let fixture: ComponentFixture<DetailPretComponent>;
        let service: DetailPretService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [DetailPretComponent],
                providers: [
                    DetailPretService
                ]
            })
            .overrideTemplate(DetailPretComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetailPretComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailPretService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DetailPret(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.detailPrets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
