/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { PiecesDetailComponent } from '../../../../../../main/webapp/app/entities/pieces/pieces-detail.component';
import { PiecesService } from '../../../../../../main/webapp/app/entities/pieces/pieces.service';
import { Pieces } from '../../../../../../main/webapp/app/entities/pieces/pieces.model';

describe('Component Tests', () => {

    describe('Pieces Management Detail Component', () => {
        let comp: PiecesDetailComponent;
        let fixture: ComponentFixture<PiecesDetailComponent>;
        let service: PiecesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [PiecesDetailComponent],
                providers: [
                    PiecesService
                ]
            })
            .overrideTemplate(PiecesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PiecesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PiecesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Pieces(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pieces).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
