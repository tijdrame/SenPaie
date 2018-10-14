import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SituationMatrimoniale } from './situation-matrimoniale.model';
import { SituationMatrimonialeService } from './situation-matrimoniale.service';

@Injectable()
export class SituationMatrimonialePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private situationMatrimonialeService: SituationMatrimonialeService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.situationMatrimonialeService.find(id)
                    .subscribe((situationMatrimonialeResponse: HttpResponse<SituationMatrimoniale>) => {
                        const situationMatrimoniale: SituationMatrimoniale = situationMatrimonialeResponse.body;
                        this.ngbModalRef = this.situationMatrimonialeModalRef(component, situationMatrimoniale);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.situationMatrimonialeModalRef(component, new SituationMatrimoniale());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    situationMatrimonialeModalRef(component: Component, situationMatrimoniale: SituationMatrimoniale): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.situationMatrimoniale = situationMatrimoniale;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
