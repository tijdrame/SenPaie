import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { StatutDemandeComponent } from './statut-demande.component';
import { StatutDemandeDetailComponent } from './statut-demande-detail.component';
import { StatutDemandePopupComponent } from './statut-demande-dialog.component';
import { StatutDemandeDeletePopupComponent } from './statut-demande-delete-dialog.component';

@Injectable()
export class StatutDemandeResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const statutDemandeRoute: Routes = [
    {
        path: 'statut-demande',
        component: StatutDemandeComponent,
        resolve: {
            'pagingParams': StatutDemandeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.statutDemande.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'statut-demande/:id',
        component: StatutDemandeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.statutDemande.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statutDemandePopupRoute: Routes = [
    {
        path: 'statut-demande-new',
        component: StatutDemandePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.statutDemande.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'statut-demande/:id/edit',
        component: StatutDemandePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.statutDemande.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'statut-demande/:id/delete',
        component: StatutDemandeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.statutDemande.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
