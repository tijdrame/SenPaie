/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { RemboursementDialogComponent } from '../../../../../../main/webapp/app/entities/remboursement/remboursement-dialog.component';
import { RemboursementService } from '../../../../../../main/webapp/app/entities/remboursement/remboursement.service';
import { Remboursement } from '../../../../../../main/webapp/app/entities/remboursement/remboursement.model';
import { DetailPretService } from '../../../../../../main/webapp/app/entities/detail-pret';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { BulletinService } from '../../../../../../main/webapp/app/entities/bulletin';

describe('Component Tests', () => {

    describe('Remboursement Management Dialog Component', () => {
        let comp: RemboursementDialogComponent;
        let fixture: ComponentFixture<RemboursementDialogComponent>;
        let service: RemboursementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [RemboursementDialogComponent],
                providers: [
                    DetailPretService,
                    UserService,
                    BulletinService,
                    RemboursementService
                ]
            })
            .overrideTemplate(RemboursementDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RemboursementDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RemboursementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Remboursement(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.remboursement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'remboursementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Remboursement();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.remboursement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'remboursementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
